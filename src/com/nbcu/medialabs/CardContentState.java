/*
 * Copyright (c) 2014 NBCUniversal, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
