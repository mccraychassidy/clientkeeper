package com.nashss.se.clientkeeper.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.clientkeeper.activity.requests.GetDeliveredOrdersRequest;
import com.nashss.se.clientkeeper.activity.results.GetDeliveredOrdersResult;

/**
 * AWS Lambda function handler for getting orders with a delivered date.
 */
public class GetDeliveredOrdersLambda extends LambdaActivityRunner<GetDeliveredOrdersRequest, GetDeliveredOrdersResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetDeliveredOrdersRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to get orders with a delivered date.
     *
     * @param input   the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the delivered orders retrieval
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetDeliveredOrdersRequest> input, Context context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims -> {
                    String userEmail = claims.get("email");
                    return GetDeliveredOrdersRequest.builder()
                            .withUserEmail(userEmail)
                            .build();
                }),
                (request, serviceComponent) ->
                        serviceComponent.provideGetDeliveredOrdersActivity().handleRequest(request)
        );
    }
}
