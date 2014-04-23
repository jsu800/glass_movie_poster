package com.nbcu.medialabs;

public enum CardContentState {
	
	DEFAULT("Default"),
	ZEETAG("NINJA"),
	WIDGET_CUSTOM1("Custom1"),
	WIDGET_CUSTOM2("Custom2"),
	WIDGET_VIDEO_SHARE("Video"),
	WIDGET_IMAGE_SHARE("Image"),
	WIDGET_TEXT_SHARE("Text"),
	WIDGET_QUOTE_SHARE("Quote"),
	WIDGET_GIF_SHARE("GIF"),
	WIDGET_PROMO("Promo"),
	BARCODE_DEMO("Barcode"),
	WIDGET_COUPON("Coupon"),
	WIDGET_FUNFACT("Funfact"),
	THE_VOICE_FUNFACT("The Voice Funfact"),
	THE_VOICE_GLADE("The Voice Coupon Glade"),
	THE_VOICE_LOREAL("The Voice Coupon Loreal"),
	THE_VOICE_PHOTO("The Voice Image Photo Share"),
	PANO_CUSTOM1("Pano 360"),
	CNBC_TEMPLATE("CNBC template"),
	WIDGET_INTRO("Introduction");
	
	private final String description;
	
	CardContentState(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}
	
}
