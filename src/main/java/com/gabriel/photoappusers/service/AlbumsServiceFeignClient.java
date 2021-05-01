package com.gabriel.photoappusers.service;

import com.gabriel.photoappusers.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "albums-service", fallback = AlbumsServiceFallback.class)
@FeignClient(name = "albums-service", fallbackFactory = AlbumsServiceFallbackFactory.class)
public interface AlbumsServiceFeignClient {

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id);

}
