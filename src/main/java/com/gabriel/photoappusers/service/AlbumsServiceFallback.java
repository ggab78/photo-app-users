package com.gabriel.photoappusers.service;

import com.gabriel.photoappusers.ui.model.AlbumResponseModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumsServiceFallback implements AlbumsServiceFeignClient{

    @GetMapping("/users/{id}/albums")
    public List<AlbumResponseModel> getAlbums(@PathVariable("id") String id){
        return new ArrayList<>();
    }

}
