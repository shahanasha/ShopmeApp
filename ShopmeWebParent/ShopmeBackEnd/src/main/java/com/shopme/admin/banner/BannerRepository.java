package com.shopme.admin.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shopme.common.entity.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer> {

//	public Banner findByName(String title);
	
	@Query("UPDATE Banner b SET b.enabled = ?2 WHERE b.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
	
	public Long countById(Integer id);
}
