package com.gabriel.photoappusers.service;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AlbumsServiceFallbackFactory implements FallbackFactory<AlbumsServiceFeignClient> {



    @Override
    public AlbumsServiceFeignClient create(Throwable cause) {

        if(cause instanceof FeignException && ((FeignException) cause).status()==404){
            log.error("Albums are not available !!!");
        }else{
            log.error(cause.getLocalizedMessage());
        }

        return new AlbumsServiceFallback();
    }
}
