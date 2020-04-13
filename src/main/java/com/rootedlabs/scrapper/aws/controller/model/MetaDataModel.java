/*******************************************************************************
 The MIT License

 Copyright (c) 2020 Mohinish (aka. rootedlabs), Siddharth (aka. spaul)

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 ******************************************************************************/

package com.rootedlabs.scrapper.aws.controller.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class MetadataModel.
 */
public class MetaDataModel<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The data. */
	@JsonInclude(Include.NON_NULL)
	@Getter
	@Setter
	private T data;

	@JsonProperty("meta")
	private final MetaModel meta = new MetaModel();

	@JsonProperty("paging")
	@JsonInclude(Include.NON_NULL)
	private Paging paging;

	public void setMessage(final String message) {
		this.meta.message = message;
	}

	@JsonIgnore
	public String getMessage() {
		return this.meta.message;
	}

	@JsonIgnore
	public void setStatus(final int status) {
		this.meta.status = status;
	}

	public Integer getStatus() {
		return this.meta.status;
	}

	public void setNext(final String nextUrl) {
		if (this.paging == null) {
			this.paging = new Paging();
		}
		this.paging.next = nextUrl;
	}

	@JsonIgnore
	public String getNext() {
		if (this.paging == null) {
			return null;
		}
		return this.paging.next;
	}

	private static class Paging {
		/** The next page. */
		@Getter
		@JsonInclude(Include.NON_NULL)
		private String next;
	}

	@JsonRootName("meta")
	private static class MetaModel implements Serializable {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The message. */
		@Getter
		private String message;

		/** The status. */
		@Getter
		private Integer status;

	}
}
