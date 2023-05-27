package com.shopme.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.shopme.common.Constants;


@Entity
@Table(name = "banners")
public class Banner {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 128, nullable = false)
	private String image;
	
   
	@Column(nullable = false, length = 512 ,unique = true)
    private String title;

    @Column(length = 512, nullable = false)
    private String description;

    @Column(nullable = false, length = 8)
    private float price;

    @Column(length = 512, nullable = false)
    private   String link;

	private boolean enabled;	

    public Banner() {
    }
    

    public Banner(Integer id, String image, String title, String description, float price, String link,
			boolean enabled) {
		super();
		this.id = id;
		this.image = image;
		this.title = title;
		this.description = description;
		this.price = price;
		this.link = link;
		this.enabled = enabled;
	}


	public Banner(Integer id) {
        this.id = id;
    }
   
    public Banner(String image) {
		
		this.image = image;
	}
    
    public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

    
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	@Transient
    public String getBannerPath(){
        if (id == null ) return "/images/default_banner.png";
        return Constants.S3_BASE_URI + "/banners/" + this.id + "/" + this.image;
    }


	@Override
	public String toString() {
		return "Banner [id=" + id + ", image=" + image + ", title=" + title + ", description=" + description
				+ ", price=" + price + ", link=" + link + ", enabled=" + enabled + "]";
	}

   
}