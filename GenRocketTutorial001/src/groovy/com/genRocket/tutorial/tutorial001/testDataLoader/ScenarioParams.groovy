package com.genRocket.tutorial.tutorial001.testDataLoader

class ScenarioParams {
  def scenario
  def scenarioDomain
  def loopCount = 0
  def inMemory = true
  def host = 'localhost'
  def port = 4444

  def ScenarioParams(scenario, scenarioDomain) {
    this.scenario = scenario
    this.scenarioDomain = scenarioDomain
  }
}
