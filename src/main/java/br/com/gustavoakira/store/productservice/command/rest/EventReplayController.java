package br.com.gustavoakira.store.productservice.command.rest;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class EventReplayController {
	
	@Autowired
	private EventProcessingConfiguration configuration;
	@PostMapping("/event-processor/{processorName}/reset")
	public ResponseEntity<String> replayEvents(@PathVariable String event) {
		Optional<TrackingEventProcessor> trackinOptional = configuration.eventProcessor(event, TrackingEventProcessor.class);
		if(trackinOptional.isPresent()) {
			TrackingEventProcessor eventProcessor = trackinOptional.get();
			eventProcessor.shutDown();
			eventProcessor.resetTokens();
			eventProcessor.start();
			return ResponseEntity.ok().body(String.format("the event processor with a name [%s] has been reset", event));
		}else {
			return ResponseEntity.badRequest().body("The event processor with a name [%s] was not found");
		}
	}
}
