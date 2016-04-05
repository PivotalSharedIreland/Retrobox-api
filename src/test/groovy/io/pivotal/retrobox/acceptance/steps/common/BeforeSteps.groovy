package io.pivotal.retrobox.acceptance.steps.common

import io.pivotal.retrobox.acceptance.common.BaseApiClient
import cucumber.api.groovy.Hooks

this.metaClass.mixin(Hooks)

Before {
  BaseApiClient.givenApiClient().expect().statusCode(200).get('/health')
}