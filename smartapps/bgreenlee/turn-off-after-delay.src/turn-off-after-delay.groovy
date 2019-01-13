/**
 *  Turn off After Delay
 *
 *  Copyright 2019 Brad Greenlee
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Turn off After Delay",
    namespace: "bgreenlee",
    author: "Brad Greenlee",
    description: "Turn a switch off after a given amount of time.",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Turn off this light") {
        input "theswitch", "capability.switch", required: true
    }
    
    section("After this amount of time") {
        input name: "delay", type: "number", title: "Delay", description: "...after this many minutes", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(theswitch, "switch.on", switchOnHandler)
}

def switchOnHandler(evt) {
    log.debug "switchOnHandler called: $evt"
    runIn(60 * settings.delay, turnSwitchOff)
}

def turnSwitchOff() {
	log.debug "turnSwitchOff called"
    theswitch.off()
}
