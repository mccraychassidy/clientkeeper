package com.nashss.se.clientkeeper.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.clientkeeper.activity.requests.DeleteClientRequest;
import com.nashss.se.clientkeeper.activity.results.DeleteClientResult;

/**
 * AWS Lambda function handler for deleting a client.
 */
public class DeleteClientLambda extends LambdaActivityRunner<DeleteClientRequest, DeleteClientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteClientRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to delete a client.
     *
     * @param input the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the client deletion
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteClientRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    DeleteClientRequest unauthenticatedRequest = input.fromBody(DeleteClientRequest.class);
                    return input.fromUserClaims(claims ->
                            DeleteClientRequest.builder()
                                    .withUserEmail(claims.get("email"))
                                    .withClientId(unauthenticatedRequest.getClientId())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteClientActivity().handleRequest(request)
        );
    }
}
