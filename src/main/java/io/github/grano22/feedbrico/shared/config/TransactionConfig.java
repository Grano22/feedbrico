package io.github.grano22.feedbrico.shared.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    @Bean
    public Advisor readOperationsAdvisor(
         @Qualifier("queryTransactionManager") PlatformTransactionManager transactionManager
    ) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        pointcut.setExpression("execution(* io.github.grano22.feedbrico.*.infrastructure.readstore..*.*(..))");

        Map<String, TransactionAttribute> txAttributes = new HashMap<>();

        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

        return createAdvisor(transactionManager, pointcut, txAttributes, readOnlyTx);
    }

    @Bean
    public Advisor writeOperationsAdvisor(
        @Qualifier("commandTransactionManager") PlatformTransactionManager transactionManager
    ) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

        pointcut.setExpression("execution(* io.github.grano22.feedbrico.*.infrastructure.persistance..*.*(..))");

        Map<String, TransactionAttribute> txAttributes = new HashMap<>();

        RuleBasedTransactionAttribute writeTx = new RuleBasedTransactionAttribute();
        writeTx.setReadOnly(false);
        writeTx.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);

        return createAdvisor(transactionManager, pointcut, txAttributes, writeTx);
    }

    private Advisor createAdvisor(
        PlatformTransactionManager transactionManager,
        AspectJExpressionPointcut pointcut,
        Map<String, TransactionAttribute> txAttributes,
        RuleBasedTransactionAttribute writeTx
    ) {
        writeTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        txAttributes.put("*", writeTx);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.setNameMap(txAttributes);

        TransactionInterceptor txInterceptor = new TransactionInterceptor();
        txInterceptor.setTransactionManager(transactionManager);
        txInterceptor.setTransactionAttributeSource(source);

        return new DefaultPointcutAdvisor(pointcut, txInterceptor);
    }
}