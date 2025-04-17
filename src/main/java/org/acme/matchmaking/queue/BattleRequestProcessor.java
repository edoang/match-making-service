package org.acme.matchmaking.queue;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.matchmaking.model.BattleRequest;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
public class BattleRequestProcessor {


    /**
     * Processes an incoming battle request message, maps it to a {@link BattleRequest},
     * and performs asynchronous match-making logic. Once the match-making is complete,
     * it creates and returns an outgoing message containing the updated {@link BattleRequest}.
     *
     * @param message the incoming message containing a {@link JsonObject} payload representing the {@link BattleRequest}
     * @return a {@link Uni} containing an outgoing {@link Message} with the updated {@link BattleRequest}
     * and acknowledgment logic from the original incoming message
     */
    @Incoming("battles-request")
    @Outgoing("battles-run")
    public Uni<Message<BattleRequest>> processBattleRequest(Message<JsonObject> message) {
        BattleRequest battleRequest = message.getPayload().mapTo(BattleRequest.class);

        // Simulate async match-making logic (e.g., database access or network request)
        return performAsyncMatchMaking(battleRequest)
                .map(updatedBattleRequest -> {
                    Log.info("match making: " + updatedBattleRequest.partyMember.heroName +
                            " vs " + updatedBattleRequest.partyMember.villain);
                    return Message.of(updatedBattleRequest, () -> message.ack());
                });
    }

    /**
     * Performs asynchronous match-making logic for a given battle request.
     * This method simulates asynchronous operations such as database access or network interactions.
     *
     * @param battleRequest The battle request containing necessary information such as party member, game ID, and other details.
     * @return A Uni that emits the processed battle request after the match-making operation is completed.
     */
    // Simulated async method using Uni
    private Uni<BattleRequest> performAsyncMatchMaking(BattleRequest battleRequest) {
        // Imagine this method performs non-blocking match-making logic
        return Uni.createFrom().item(battleRequest); // Replace with actual async operation
    }

}