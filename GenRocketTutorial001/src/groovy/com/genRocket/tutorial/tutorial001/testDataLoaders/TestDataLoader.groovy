package groovy.com.genRocket.tutorial.tutorial001.testDataLoaders

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * Created by htaylor on 1/17/15.
 */
class TestDataLoader {

  static runScenario(String path,
                     String accessKey,
                     String scenario,
                     String scenarioDomain,
                     Boolean inMemory = true,
                     Integer loopCount = 0,
                     String host = 'localhost',
                     Integer port = 4444) {
    def map = [
      path          : path,
      scenario      : scenario,
      scenarioDomain: scenarioDomain,
      inMemory      : inMemory,
      loopCount     : loopCount,
      accessKey     : accessKey
    ]

    def json = JsonOutput.toJson(map)
    def socket = new Socket(host, port);
    def slurper = new JsonSlurper()
    def data = null

    socket.withStreams { input, output ->
      output << json + '\n'
      data = slurper.parseText(input.newReader().readLine())
    }

    return data
  }
}
