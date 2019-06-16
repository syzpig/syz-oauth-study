## syz-admin-study   
##springcloud 用户权限框架学习搭建     
#搭建步骤：     
#一、基础框架搭建(详细配置请参考代码)     
**1.注册中心服务搭建**    
主要引入架包：   
         <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-eureka-server</artifactId>
         </dependency>   
**2.网关服务搭建**    
         <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zuul</artifactId>
		 </dependency>  	
**3.配置中心服务搭建**   
         <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		 </dependency>    
		 （1）本地创建一个配置中心服务，连接指点到git等上创建的一个配置仓库，使用该配置仓库    
**4.springboot脚手架搭建**    
   （1）、本项目搭建脚手架主要包块如下几个模块：    
   （2）、基础Dao／Mapper封装   
   （3）、基础Biz／Service封装   
   （4）、基础Controller封装   
   （5）、基础数据对象封装   
   （6）、全局异常拦截器处理以及异常工具类   
   （7）、全局当前操作信息相关类 用于操作当前线程上下文信息等 -> ThreadLocal   
   （8）、通用单元测试构建    
**5.用户服务搭建**
#二、用户权限搭建：包块用户权限(认证与授权)、服务鉴权
 **1.OAuth2原理**   
 一个安全的**授权框架**。它详细描述了系统中不同角色、用户、服务前端应用（比如API），以及客户端（比如网站或移动App）之间怎么实现相互认证。  
 四种授权模式：
 1. 授权码（认证码）模式 （Authorization code) response_type=code   **常用**   
 2. 简化（隐形）模式 (Impilict） response_type=token
 3. 用户名密码模式 (Resource Owner Password Credential)grant_type=password  **常用**    
 4. 客户端模式 (Client Credential) grant_type=client_credential   
 具体请参考：阮一峰博客：http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html  

**2.JWT原理**  
  1.JWT ：JSON Web Token，它是基于 RFC 7519 所定义的一种在各个系统中传递 紧凑 和 自包含 的 JSON 数据形式。   
  2.紧凑（Compact） ：由于传送的数据小，JWT 可以通过**GET**、**POST** 和 放在 **HTTP 的 header** 中，同时也是因为小也能传送的更快。    
  3.自包含（self-contained） : Payload 中能够包含**用户的信息**，避免数据库的查询。   
**JSON Web Token 是一段被 Base64 编码过的字符序列。由三部分组成，Token结构： Header. Payload(Claims ). Signature**  
  结构如下：   
  (1).Headers   
      包含类别(type)、加密算法(alg)    
       {   
       "alg":"HS256",  
       "typ":"JWT"  
       }    
  (2).Claims   
      包括需要传递的用户信息   
      {    
      "sub":"1234567890",   
      "name":"John Deo",   
      "admin":true   
      }   
  (3).Signature   
      根据alg算法与私有秘钥进行加密得到的签名字串；   
      这一段是最重要的敏感信息，只能在服务端解密；  
      HMACSHA256(   
          base64UrlEncide(header)+"."+base64UrlEncode(payload),   
          SECREATE_KEY  
      )   
  具体详解情况看：http://www.leftso.com/blog/220.html    
**3.JWT与oauth区别**    
1.jwt比oauth轻巧，   
2.jwt是一个授权协议，可包含用户信息，数据小，传输快，支持多种传输方式等，  
3.oauth是一个授权框架，  用的较多场景是授权码。     
4.从交互过程看，JWT只需要两次服务间交互认证授权；oauth需要三次交互才能到业务服务。    
#三、JWT认证与网关结合（这里采用JWT认证授权协议方式）
**(一)、实现步骤如下**   
1、JWT工具类封装   
2、用户验证接口实现   
3、JWT登录、注销、刷新 (注销与刷新使用redis会很快)   
4、PreFilter JWT认证实现   
5、后端服务改造   
6、前端与后端JWT交互认证   
参考方案：https://zhuanlan.zhihu.com/p/29345083   

#四、服务鉴权  
约定：根用户相关的所有请求走规范认证，就是上面的用户授权认证；     
      根用户无关的，走服务鉴权（级别比较高），有些服务不一定能直接访问的等等。   
就是在网关以内：服务与服务之间访问操作，一般是授权或是通过黑白名单等。   
##（一）、服务鉴权中心搭建    
### 鉴权设计中心    
### 鉴权开发中心   
### 1-服务注册（秘钥派发）  
### 2-服务授权（大粒度）    

每个服务都会有一个mvc拦截器和feign，mvc进行鉴权，feign进行获取token        
serverA 和serverB,都要去鉴权中心拿token，看详细方案，  A访问B,B会有多个资源，看那些可以访问他，简单就是一对多关系       
https://zhuanlan.zhihu.com/p/29345083    

##(二)、服务端agent设计 详细流程看Agent流程图.png    
 （利用agent实现自发式的小插件，去自动获取token。自动校验token等，不用每次请求都要由开发人员去获取。）    
###1、服务发起端（Fegin Client Interceptor）实现自发获取    
---->申请Token    
###2、服务调用端（MVC Interceptor）  
---->校验token    
###3、优化：RestTemplate+Ribbon (FeginClient)   
第一种方式：网关鉴权 + mvc拦截器      
服务鉴权大致流程，具体看流程图：     
首先通过MVC拦截器，认证你传过来的token是否有效，一定Aauth派发中心派发的token、       
其次在认证你是否在我的访问授权列表中，在则往下走，否侧该客户端被拒绝      
根据其调取的客户端       
再走网关过滤器 会根据的客户端去获取token，然后放入请求头中，再去请求要请求的服务，再由被请求进行token校验       
（就是网关中过滤器的（申请客户端密钥头）这一步骤）       

第二种方式：fegin拦截器获取token鉴权  鉴权拦截器是一样的与上面形式，这个fegin拦截器就是获取token      
当服务不通过网关调用时，可以通过fegin的方式去获取token进行验证      
 这里只是举例。并没有操作，具体请看项目AG-GATE   https://gitee.com/geek_qi/ace-gate    
 
#五、API权限  
##API权限拦截   
数据库模型可分为三大块：组-资源-权限     
其流程或者是检索流程思路是：     
通过JWT获取用户-》用户登进来-》去看有哪些组group（组来源：leader(领导表)表，member（成员）表）都去关联groupId-》groupId然后去关联分配的对应资源Id-》根据分配的Id去查找可访问的菜单、可以操作的页面按钮（可访问的API）等等。        
##网关权限拦截过滤器改造  

##前后端分离按钮权限管理   
具体请看服务：    
https://github.com/wxiaoqi/Spring-Cloud-Admin    
前端快速熟悉请看该项目：   
https://github.com/syzpig/vue-element-admin  



jwt用户校验流程：
1.首先用户携带用户名和密码，访问服务器，此时服务器会创建一个jwt，也就是一个token。然后返回给客户端用户，
给浏览器，然后浏览器缓存起来
2.下一次请求的时候，他就会携带这个jwt信息（token）放在头部。到达服务器后，服务器判断签名是否被篡改过，是否有限。
校验正常合法后，则返回用户信息。
根据架构流程图解释：
假如用户开始登录，通过Nginx（走）访问我们的网关。然后我的网关去根据注册，去拉取用户的的注册中心。然后验证用户的有效性，如果有效
之后，我会去调用jwt的组件。去派发一个token返回给用户。然后用户再根据派发的token.再次携带token去请求网关，然后网关再次校验
，校验通过后，传给后端服务，然后后端服务根据token去获取对应的有效信息。然后去做相关事情。只要登录进来，所有的操作都会携带这个sccess-token去操作。
所以说检验的有效性操作全在网关这里操作。

根据jwt流程。可知：
Authxxxxxxxservice服务就相当于，鉴权中心（授权服务器）， 有一个private key 吗
Rourcexxxxxxxservice服务就相当于，内部服务，所有后台微服务。他们都有一个Public key
由此可看他自带非对称加密认证。有一对秘钥，公钥和私钥后，有了他们，jwt就减少了无必要的请求了。就是秘钥加密的话，后台服务器不必要再去请求
授权服务器去解密，我们直接根据公钥验证这个token是否有效，如果通过公钥解密看是否成功（这个JWT 就是token，他里面是有一个签名的，只有在服务器端才能解密，里面的东西，作用就是来确定他是否能不能被解密，当jwt的信息改变了，里面有个签名，后面就无法改变的）
，则获取到用户信息，直接去操作后台服务，。省了一步，去访问授权服务器的步骤。


本框架，采用的是cookie存储方式。




服务鉴权原理：




