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

package com.rootedlabs.scrapper.aws.controller;

import java.util.Locale;

import com.rootedlabs.scrapper.aws.controller.model.MetaDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class AbstractController.
 */
@Controller
@RequestMapping("/api")
@CrossOrigin("http://127.0.0.1:8080")
public abstract class AbstractController {

	/** The Constant MSG_OK. */
	public static final String MSG_OK = "Success.";

	/** The Constant MSG_BAD_REQUEST. */
	public static final String MSG_BAD_REQUEST = "Request failed.";

	/** The Constant MSG_ISE. */
	public static final String MSG_ISE = "Server error.";

	/** The Constant MSG_UNAUTHORIZED. */
	public static final String MSG_UNAUTHORIZED = "Unauthorized.";

	@Autowired
	private MessageSource messageSouce;

	/**
	 * Ok response.
	 *
	 * @return the response entity
	 */
	public ResponseEntity<MetaDataModel<Object>> ok() {
		final MetaDataModel<Object> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.OK.value());
		metaDataModel.setMessage(MSG_OK);
		return new ResponseEntity<>(metaDataModel, HttpStatus.OK);
	}

	/**
	 * Ok response.
	 *
	 * @param <T> the generic type
	 * @param data the data
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> ok(final T data) {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.OK.value());
		metaDataModel.setMessage(MSG_OK);
		metaDataModel.setData(data);
		return new ResponseEntity<>(metaDataModel, HttpStatus.OK);
	}

	/**
	 * Ok.
	 *
	 * @param <T> the generic type
	 * @param data the data
	 * @param messageKey the message key or message itself
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> ok(final T data, final String messageKey) {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.OK.value());
		metaDataModel.setMessage(msg(messageKey));
		metaDataModel.setData(data);
		return new ResponseEntity<>(metaDataModel, HttpStatus.OK);
	}

	/**
	 * Ok.
	 *
	 * @param <T> the generic type
	 * @param data the data
	 * @param includePaging the include paging
	 * @param nextPage the next page
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> ok(final T data, final boolean includePaging, final String nextPage) {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.OK.value());
		metaDataModel.setMessage(MSG_OK);
		metaDataModel.setData(data);
		metaDataModel.setNext(nextPage);
		return new ResponseEntity<>(metaDataModel, HttpStatus.OK);
	}

	/**
	 * Bad request.
	 *
	 * @param <T> the generic type
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> badRequest() {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.BAD_REQUEST.value());
		metaDataModel.setMessage(MSG_BAD_REQUEST);
		return new ResponseEntity<>(metaDataModel, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Bad request.
	 *
	 * @param messageKey the message key or message itself
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> badRequest(final String messageKey) {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.BAD_REQUEST.value());
		metaDataModel.setMessage(msg(messageKey));
		return new ResponseEntity<>(metaDataModel, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Internal server error.
	 *
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> internalServerError() {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		metaDataModel.setMessage(MSG_ISE);
		return new ResponseEntity<>(metaDataModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Internal server error.
	 *
	 * @param messageKey the message key
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> internalServerError(final String messageKey) {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		metaDataModel.setMessage(msg(messageKey));
		return new ResponseEntity<>(metaDataModel, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Unauthorized.
	 *
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> unauthorized() {
		final MetaDataModel<T> metaDataModel = new MetaDataModel<>();
		metaDataModel.setStatus(HttpStatus.UNAUTHORIZED.value());
		metaDataModel.setMessage(MSG_UNAUTHORIZED);
		return new ResponseEntity<>(metaDataModel, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Unauthorized.
	 *
	 * @param messageKey the message
	 * @return the response entity
	 */
	public <T> ResponseEntity<MetaDataModel<T>> unauthorized(final String messageKey) {
		final MetaDataModel<T> metaModel = new MetaDataModel<>();
		metaModel.setStatus(HttpStatus.UNAUTHORIZED.value());
		metaModel.setMessage(msg(messageKey));
		return new ResponseEntity<>(metaModel, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Key to message.
	 *
	 * @param key the key
	 * @return the string
	 */
	protected String msg(final String key) {
		try {
			return this.messageSouce.getMessage(key, null, Locale.getDefault());
		} catch (final NoSuchMessageException e) {
			return key;
		}
	}
}
