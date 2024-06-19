package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.GetAllClientsRequest;
import com.nashss.se.clientkeeper.activity.results.GetAllClientsResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllClientsLambda extends LambdaActivityRunner<GetAllClientsRequest, GetAllClientsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetAllClientsRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetAllClientsRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromUserClaims(claims -> {
                String userEmail = claims.get("email");
                return GetAllClientsRequest.builder()
                        .withUserEmail(userEmail)
                        .build();
            }),
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllClientsActivity().handleRequest(request)
        );
    }
}
