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

package com.rootedlabs.scrapper.aws.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "EC2")
public class EC2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "_ID")
	private Long id;

	@Column(name = "INSTANCE_ID")
	private String instanceId;

	@Column(name = "INSTANCE_TYPE")
	private String instanceType;

	@Column(name = "REGION")
	private String region;

	@Column(name = "INSTANCE_STATE")
	private String state;

	@Column(name = "PUBLIC_DNS")
	private String publicDNSName;

	@Column(name = "PRIVATE_DNS")
	private String privateDNSName;

	@Column(name = "PUBLIC_IP_ADDRESS")
	private String publicIpAddress;

	@Column(name = "PRIVATE_IP_ADDRESS")
	private String privateIpAddress;

	@Column(name = "OWNER_ID")
	private String ownerId;

	@Column(name = "CORE_COUNT")
	private String coreCount;

	@JsonProperty("tags_str")
	@Column(name = "TAGS", length=2000)
	private String tags;

	@JsonIgnore
	@Column(name = "AWS_GROUPS")
	private String groups;

	@Column(name = "SUBNET_ID")
	private String subnetId;

	@Column(name = "KEY_NAME")
	private String keyName;

	@Column(name = "LAUNCH_TIME")
	private LocalDateTime launchTime;

	@JsonIgnore
	@JoinColumn(name = "SCRAPE_ID", nullable = false)
	@ManyToOne(targetEntity = Scrape.class, fetch = FetchType.LAZY)
	private Scrape scrape;

	@Transient
	@JsonProperty("groups")
	public List<String> groupAsJson() {
		return Arrays.asList(groups.split(","));
	}

	@Transient
	@JsonProperty("tags")
	public Map<String, String> tagsAsJson() {
		Map<String, String> result = new HashMap<>();
		if (!tags.isBlank()) {
			result = Arrays.asList(tags.split(",")).stream().map(s -> s.split("=")).collect(Collectors.toMap(k -> k[0], v -> {
				return v.length > 1 ? v[1] : "";
			}));
		}
		return result;
	}
}
