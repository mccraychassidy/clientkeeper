package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.GetUndeliveredOrdersRequest;
import com.nashss.se.clientkeeper.activity.results.GetUndeliveredOrdersResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for getting orders without a delivered date.
 */
public class GetUndeliveredOrdersLambda extends
        LambdaActivityRunner<GetUndeliveredOrdersRequest, GetUndeliveredOrdersResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetUndeliveredOrdersRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to get orders without a delivered date.
     *
     * @param input   the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the undelivered orders retrieval
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetUndeliveredOrdersRequest> input,
                                        Context context) {
        return super.runActivity(
            () -> input.fromUserClaims(claims -> {
                String userEmail = claims.get("email");
                return GetUndeliveredOrdersRequest.builder()
                        .withUserEmail(userEmail)
                        .build();
            }),
            (request, serviceComponent) ->
                    serviceComponent.provideGetUndeliveredOrdersActivity().handleRequest(request)
        );
    }
}
