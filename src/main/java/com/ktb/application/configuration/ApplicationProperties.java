package com.ktb.application.configuration;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties {

	@Value("${userBucket.path}")
	private String userBucketPath;
}
