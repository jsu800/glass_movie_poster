/*
 * Copyright (c) 2014 Joseph Su
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

package com.nbcu.medialabs.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;

/* 
 * @author Joseph Su
 */

@Entity
public class FileUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Basic
    private Long subjectId;
	
	@Basic
	private BlobKey blob;
	
	@Basic
    private int blobSize;

	public FileUpload() {
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public BlobKey getBlob() {
		return blob;
	}

	public void setBlob(BlobKey blob) {
		this.blob = blob;
	}

	public int getBlobSize() {
		return blobSize;
	}

	public void setBlobSize(int blobSize) {
		this.blobSize = blobSize;
	}
	   
}