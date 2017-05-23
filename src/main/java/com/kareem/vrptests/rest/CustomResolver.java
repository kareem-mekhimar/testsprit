/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kareem.vrptests.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Bridge
 */

@Provider
public class CustomResolver implements ContextResolver<ObjectMapper>{

    @Override
    public ObjectMapper getContext(Class<?> type) {
      
        ObjectMapper mapper = new ObjectMapper() ;
        
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) ;
        
        return mapper ;
    }
    
}
