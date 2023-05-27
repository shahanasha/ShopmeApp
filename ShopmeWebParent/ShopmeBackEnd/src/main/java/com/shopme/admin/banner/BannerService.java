package com.shopme.admin.banner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopme.common.entity.Banner;
import com.shopme.common.exception.CategoryNotFoundException;

import java.util.List;

@Service
@Transactional
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;


    public Banner save(Banner banner){
        Banner savedBanner  = bannerRepository.save(banner);
        return savedBanner;
    }

    public List<Banner> findById() {
        return (List<Banner>) bannerRepository.findAll();
    }

    public Banner findById(int id) {
        Banner banner = bannerRepository.findById(id).get();

        return banner;
    }
    
    public void updateProductEnabledStatus(Integer id, boolean enabled)
    {
    	bannerRepository.updateEnabledStatus(id, enabled);
    }
    
    public void delete(Integer id) throws CategoryNotFoundException {
		Long countById = bannerRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new CategoryNotFoundException("Could not find any category with ID " + id);
		}
		
		bannerRepository.deleteById(id);
	}	

}