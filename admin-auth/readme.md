该服务是：构建授权服务器

把模拟用户token派发功能提取到该服务，使用网关功能是实现负载均衡，之前只是模拟用户认证与token派发





服务鉴权原理：
根据 服务鉴权内部原理.jpg流程图
一、首先根据左上角整个完整流程图来看
1.当Service1去请求其他服务时；首先是Service1去鉴权中心注册，并申请token；
然后鉴权中心生成一个token返回给客户端服务Service1。
2.然后Service1携带token去请求服务2Service2。
3.再然后Service2会携带token去请求鉴权中心，同时把服务器编号也发过去。然后问鉴权中心这个token可不可以访问我这个服务
，如果鉴权中心说这个token是有效的，是可以的。这时Service2就会把校验的结果返回给请求的客户端服务Service1.
这个整个服务鉴权的整体逻辑
二、下面根据右下角流程图，说下实际的内部细节
在不考虑外部web端请求的情况下，通过euraka层间的交互。
1.首先Service1去通过Feign去调用Service2，在调用之前，我们先会通过FeginClient，
所以我们先定义一个fegin拦截器，就是在请求之前，自发的去鉴权中心去请求token，
2.然后鉴权中心就返回token到这个fegin拦截器，
3.再然后，通过拿到的tokrn（注意：这里的请求可能也来自外部的web端，此时还需要携带用户上下文信息，如图）去请求
Service2，此时我们的Service2里面植入了一个mvc拦截器，他就会去鉴权中心去检验这个token是不是合法，有没有权限访问
我这里面的接口等等。通过的话就会返回结果给Service1.
到这里就是服务鉴权的细节原理了。

总结：
这里实际上就是利用，在各个服务上植入的客户端，每个服务都携带两个客户端，一个是mvc拦截器一个是feginclient拦截器
；这样做的目的就是请求之前自动去获取token，然后携带token去校验，然后访问的请求被拦截下来去做校验，成功则继续往下走，不
成功则返回校验结果。

三、根据服务鉴权内部原理2.jpg流程图，可以看到整个流程
1.由图知，正常的逻辑都是页面请求，然后从网关下来，再到我们的鉴权中心这一块。用户请求到这一块，先是通过mvc拦截器
去校验用户token的合法性，同时初始化用户上下文信息。
2.然后我们还有一种情况就是，服务不是从网关过来的，是服务内部之间的请求。
如果某一天我们的euraka在内网被暴露了，这时候，我一个恶意的开发人员，也弄了一个euraka或服务，他也往我们的euraka去注册，
如果我们内部没有服务鉴权的话，我们的内部就很通透，他可以去掉其他服务该金额等等违法操作。这样我们的服务内部是非常不安全的。



在开源的Cloud-Platform项目中
实战
一、服务鉴权的具体使用流程
也是包含两个拦截，一个是mvc拦截器和OkHttp连接器，（等价于feginClient拦截器）
因为该平台是用OkHttp代替了feginClient进行fegin请求。

该平台的服务鉴权服务做到了自动化的进行服务鉴权。因为他把服务鉴权的拦截器和token产生与操作的方式全都在服务鉴权中实现
各个服务直接引入该服务拦截就可以完成服务鉴权，无需手动实现鉴权的判断与token解析判断等操作
1、在服务鉴权的Auth-Client模块中有三个拦截器
OkHttpTokenInterceptor      这就是鉴权中客户端服务拦截器  由他根据配置文件获取该服务标识生成token  然后放在请求头部  @AutoConfigureBefore(FeignAutoConfiguration.class)  在进行服务调用时，自动去鉴权中心拉取token,然后请求服务
ServiceAuthRestInterceptor  这就是鉴权中服务端服务拦截器  由他从请求Header中获取请求服务的token，解析token，获取该服务标识，然后与该服务可访问列表对比判断是否可以访问。
UserAuthRestInterceptor     这就是用户拦截器，判断用户是否合法，web端拦截器

2、token申请
ServiceAuthUtil 他会读取配置中的关于服务鉴权的基础配置  
该工具会自动刷新获取token

其中AuthClientRunner  他实现了CommandLineRunner接口
平常开发中有可能需要实现在项目启动后执行的功能，SpringBoot提供的一种简单的实现方案就是添加一个model并实现CommandLineRunner接口，实现功能的代码放在实现的run方法中
在项目启动时他去初始化服务鉴权和用户认证公钥  里面携带用户或服务信息

获取配置文件中客户服务和用户相关标识token等属性，的工具类在AutoConfiguration这个类中初始化。


二、用户校验流程
1、JWT的请求与派发
（1）.ace-auth-servr 模块中的AuthController 的createAuthenticationToken法 获取token
（2）.AuthServiceImpl 的login 中通过Feign 远程调⽤进⾏账户密码有效性校验
（3）.IUserService 中的validate ⽅法对应ace-admin 模块中的UserRest 中的validate ⽅
法
2、后端服务JWT解析
  （1）.后端JWT的解析已经集成在ace-auth-client 模块中，我们只需要进⾏如下配置即可
        <dependency>
        <groupId>com.github.wxiaoqi</groupId>
        <artifactId>ace-auth-client</artifactId>
        <version>2.0-SNAPSHOT</version>
        </dependency>
  （2）.启动类添加注解
      @EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign"})
      @EnableAceAuthClient
  （3）.配置拦截器
      @Configuration("admimWebConfig")
      @Primary
      public class WebConfiguration implements WebMvcConfigurer {}
      
  （4）.利用⽤mvc拦截器对访问请求的header中的token进行有效性判断，具体
  类：UserAuthRestInterceptor

