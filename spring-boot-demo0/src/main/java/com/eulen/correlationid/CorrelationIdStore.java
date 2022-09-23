package com.eulen.correlationid;

import org.springframework.stereotype.Component;

import com.eulen.logger.ILogger;
import com.eulen.logger.LoggerLog4J;
 
 

@Component
public class CorrelationIdStore   {

   static ILogger log = LoggerLog4J.getLogger(CorrelationIdFilter.class);


   public static ThreadLocal<CorrelationIdToken> store = new ThreadLocal<>();

   private static Integer count=1;
     
   public static String getCurrentId()  { 
      CorrelationIdToken tk = store.get();
      
      if (tk==null) tk= generateNewId(); 
      return tk.getCorrelationId();
     
   }
   public static void setRequest (String req)  {
      CorrelationIdToken tk = new CorrelationIdToken(req);
      store.set(tk);
      log.debug("Creacion Id Token:  " );
      CorrelationIdStore.count++; 
   }
   public static void newRequest ()  {
      CorrelationIdToken tk = new CorrelationIdToken(CorrelationIdStore.count);
      store.set(tk);
      log.debug("Creacion Id Token:  " );
      CorrelationIdStore.count++; 
   }
   private static CorrelationIdToken generateNewId()  {
      CorrelationIdToken tk = new CorrelationIdToken(CorrelationIdStore.count);
      store.set(tk);
      log.debug("Creacion Id Token:  ");
      CorrelationIdStore.count++;
      return tk;
   }
   public static String generateComplexId()  {
      
     //TODO No implementado
     throw new RuntimeException("No implementado");
     
   }
 
}