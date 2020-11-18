package com.template.webserver

import com.template.flows.IOUFlow
import net.corda.core.identity.CordaX500Name
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class Controller(rpc: NodeRPCConnection) {

    companion object {
        private val logger = LoggerFactory.getLogger(RestController::class.java)
    }

    private val proxy = rpc.proxy

    @GetMapping(value = ["/iou"], produces = ["text/plain"])
    private fun startBatch(): String {
        val otherParty = proxy.wellKnownPartyFromX500Name(CordaX500Name.parse("O=PartyB,L=New York,C=US"))
        for (x in 0..50) {
            val flow = proxy.startFlowDynamic(IOUFlow.Initiator::class.java, x, otherParty)
            println("$x - ${flow.id} - Flow Complete.")
        }
        return "Complete"
    }
}