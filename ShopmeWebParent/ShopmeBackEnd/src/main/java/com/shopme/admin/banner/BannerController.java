package com.shopme.admin.banner;

import com.shopme.admin.AmazonS3Util;
import com.shopme.common.entity.Banner;
import com.shopme.common.exception.CategoryNotFoundException;
import com.shopme.common.exception.OrderNotFoundException;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class BannerController {

    @Autowired
    private BannerService bannerService;


//    @GetMapping("/banners")
//    public String bannerPage(Model model){
//        Banner banner1 = bannerService.findById(1);
//        Banner banner2 = bannerService.findById(2);
//        Banner banner3 = bannerService.findById(3);
//        Banner fullWidthBanner = bannerService.findById(4);
//
//        model.addAttribute("banner1",banner1);
//        model.addAttribute("banner2",banner2);
//        model.addAttribute("banner3",banner3);
//        model.addAttribute("fullWidthBanner",fullWidthBanner);
//        model.addAttribute("pageTitle","Banners");
//        return "banner/banners";
//    }
    @GetMapping("/banners")
    public String listAll(Model model)
    {
    	List<Banner> listBanner=bannerService.findById();
    	
    	model.addAttribute("listBanner",listBanner);
    	return "banner/banners";
    }
    	
     

//    @PostMapping("/banners/save_banner1")
//    public String saveBanner1(Banner banner1, @RequestParam(name = "id_image1") MultipartFile multipartFile, @RequestParam(name = "banner1Image") String bannerImage,
//                              RedirectAttributes redirectAttributes) throws IOException {
//
//        banner1.setImage(bannerImage);
//
//        redirectAttributes.addFlashAttribute("message","The Banner 1 has been saved Successfully!");
//        return checkMultipartAndSave(banner1, multipartFile, redirectAttributes);
//    }
//
//    @PostMapping("/banners/save_banner2")
//    public String saveBanner2(Banner banner2, @RequestParam(name = "id_image2") MultipartFile multipartFile, @RequestParam(name = "banner2Image") String bannerImage,
//                              RedirectAttributes redirectAttributes) throws IOException {
//
//        banner2.setImage(bannerImage);
//
//        redirectAttributes.addFlashAttribute("message","The Banner 2 has been saved Successfully!");
//        return checkMultipartAndSave(banner2, multipartFile, redirectAttributes);
//    }
//
//    @PostMapping("/banners/save_banner3")
//    public String saveBanner3(Banner banner3, @RequestParam(name = "id_image3") MultipartFile multipartFile, @RequestParam(name = "banner3Image") String bannerImage,
//                              RedirectAttributes redirectAttributes) throws IOException {
//        banner3.setImage(bannerImage);
//
//        return checkMultipartAndSave(banner3, multipartFile, redirectAttributes);
//    }
//
//    @PostMapping("/banners/save_banner4")
//    public String saveBanner4(Banner fullWidthBanner, @RequestParam(name = "id_image4") MultipartFile multipartFile, @RequestParam(name = "banner4Image") String bannerImage,
//                              RedirectAttributes redirectAttributes) throws IOException {
//        fullWidthBanner.setImage(bannerImage);
//
//        return checkMultipartAndSave(fullWidthBanner, multipartFile, redirectAttributes);
//    }
//
//    @NotNull
//    private String checkMultipartAndSave(Banner banner,MultipartFile multipartFile, RedirectAttributes redirectAttributes) throws IOException {
//        Integer id = banner.getId();
//        System.out.println(banner.getImage());
//        if (!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            banner.setImage(fileName);
//            Banner savedBanner = bannerService.save(banner);
//            String uploadDir = "banners/"+savedBanner.getId();
//
////            AmazonS3Util.removeFolder(uploadDir);
////            AmazonS3Util.uploadFile(uploadDir, fileName, multipartFile.getInputStream());
//        }else{
//            bannerService.save(banner);
//        }
//
//        redirectAttributes.addFlashAttribute("message","The Banner " + id + " has been saved Successfully!");
//        return "redirect:/banners";
//    }
    
    @GetMapping("/banners/{id}/enabled/{status}")
	public String updateProductEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		bannerService.updateProductEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Product ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/banners";
	}
    
    @GetMapping("/banners/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id") Integer id, 
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			bannerService.delete(id);
//			String categoryDir = "category-images/" + id;
//			AmazonS3Util.removeFolder(categoryDir);
			
			//FileUploadUtil.removeDir(categoryDir);
			
			redirectAttributes.addFlashAttribute("message", 
					"The category ID " + id + " has been deleted successfully");
		} catch (CategoryNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		}
		
		return "redirect:/banners";
	}
    
    
}