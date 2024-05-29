package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.CreateClientRequest;
import com.nashss.se.clientkeeper.activity.results.CreateClientResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for creating a new client.
 */
public class CreateClientLambda extends LambdaActivityRunner<CreateClientRequest, CreateClientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateClientRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to create a new client.
     *
     * @param input the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the client creation
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateClientRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateClientRequest unauthenticatedRequest = input.fromBody(CreateClientRequest.class);
                return input.fromUserClaims(claims ->
                        CreateClientRequest.builder()
                                .withUserEmail(claims.get("email"))
                                .withClientId(unauthenticatedRequest.getClientId())
                                .withClientName(unauthenticatedRequest.getClientName())
                                .withClientEmail(unauthenticatedRequest.getClientEmail())
                                .withClientPhone(unauthenticatedRequest.getClientPhone())
                                .withClientAddress(unauthenticatedRequest.getClientAddress())
                                .withClientMemberSince(unauthenticatedRequest.getClientMemberSince())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideCreateClientActivity().handleRequest(request)
        );
    }
}
