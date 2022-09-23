package com.eulen.correlationid;
 
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class CorrelationIdToken   {
   private long creation;
   private String correlationId;


   public long getCreation() {
	return creation;
}
public void setCreation(long creation) {
	this.creation = creation;
}
public String getCorrelationId() {
	return correlationId;
}
public void setCorrelationId(String correlationId) {
	this.correlationId = correlationId;
}
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