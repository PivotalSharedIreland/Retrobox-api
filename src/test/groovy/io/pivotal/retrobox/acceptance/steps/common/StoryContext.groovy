package io.pivotal.retrobox.acceptance.steps.common


class StoryContext {

  static class Fields {
    public static String RESPONSE = 'response'
  }

  static Map context = [:]

  static def getFromContext(String key) {
    context.get(key)
  }

  static def putInContext(def key, def value) {
    context.put(key, value)
  }
}