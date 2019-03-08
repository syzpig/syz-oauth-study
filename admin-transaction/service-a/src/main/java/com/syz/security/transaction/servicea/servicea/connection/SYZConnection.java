package com.syz.security.transaction.servicea.servicea.connection;

import com.syz.security.transaction.servicea.servicea.transactional.SYZTransactional;
import com.syz.security.transaction.servicea.servicea.transactional.TransactionType;
import com.syz.security.transaction.servicea.servicea.utils.Task;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 手动实现数据库连接connection
 * 我们数据事务是基于连接的connect,所以我们要控制本地数据库事务，就要重写这个connection数据连接方法。
 */
public class SYZConnection implements Connection {
    private Connection connection;
    private Task task; //把task传进来
    private SYZTransactional syzTransactional;

    public SYZConnection(Connection connection) {
        this.connection = connection;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public void commit() throws SQLException {
        //我们仅仅去操作commit其实对spring是没有影响的，
        //所以按照我们的逻辑我们只要在执行这个方法之前等待一下，等一个信号过来之后，我再去提交或者回滚。
        //所以我们直接实现commit,rollback,close这三个方法就好了，其他的方法直接默认spring实现就好了。例如：createStatement
        //而我们重写这三个方法，也不是完完全全重写，借用spring返回的链接，我们也就拿到了springconnection数据库事务的控制权，
        //至此解决了我们第一个问题，拿到了事务的控制权

        //wait........
        //那么这个等待怎么实现呢？  通过一个LOCK   阻塞，await()
        // syzTransactional.getTask().waitTask();  //通过这一步，但是直接这么写是有问题的，因为这么写整个程序到此就被阻塞了
        //，下面就没法再去运行，通知等等操作了，变成了死锁。
        //所以可以通过多线程去操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                syzTransactional.getTask().waitTask();
                try {
                    if (syzTransactional.getTransactionType().equals(TransactionType.rollback)) {
                        connection.rollback();
                    } else {
                        connection.commit();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    //todo 这里要考虑万一失败怎么？事务补偿机制
                }
            }
        }).start();
        //开辟新的线程去等待。主线程相当于啥也没干，spring这步的commit操作就想当啥也没做。
    }

    @Override
    public void rollback() throws SQLException {
        connection.rollback();//我们仅仅去操作commit其实对spring是没有影响的，
    }

    @Override
    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return connection.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return connection.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {

    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return false;
    }


    @Override
    public void close() throws SQLException {

    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {

    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return false;
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {

    }

    @Override
    public String getCatalog() throws SQLException {
        return getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {

    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return 0;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        return createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return prepareStatement(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        return prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {

    }

    @Override
    public int getHoldability() throws SQLException {
        return 0;
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        connection.rollback();
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {

    }

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        return null;
    }

    @Override
    public Clob createClob() throws SQLException {
        return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
        return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
        return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return false;
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {

    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {

    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return null;
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {

    }

    @Override
    public String getSchema() throws SQLException {
        return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }
}
