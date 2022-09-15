package com.eulen.correlationid;
 
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class CorrelationIdToken   {
   private long creation;
   private String correlationId;


   public CorrelationIdToken() { 
      this.creation = System.currentTimeMillis(); 
      this.correlationId = UUID.randomUUID().toString();; 
   }
   public CorrelationIdToken( int count)  { 
      this.creation = System.currentTimeMillis(); 
      this.correlationId = "" + count; 
   }
   public CorrelationIdToken( String correlation)  { 
      this.creation = System.currentTimeMillis(); 
      this.correlationId = correlation; 
   }
   
}