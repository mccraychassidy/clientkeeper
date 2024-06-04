package com.nashss.se.clientkeeper.lambda;

import com.nashss.se.clientkeeper.activity.requests.GetClientRequest;
import com.nashss.se.clientkeeper.activity.results.GetClientResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * AWS Lambda function handler for getting a client.
 */
public class GetClientLambda extends LambdaActivityRunner<GetClientRequest, GetClientResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetClientRequest>, LambdaResponse> {

    /**
     * Handles the Lambda function request to get a client.
     *
     * @param input the authenticated request
     * @param context the Lambda execution environment context
     * @return result of the client retrieval
     */
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetClientRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPathAndQuery((path, query) ->
                    GetClientRequest.builder()
                            .withUserEmail(query.get("email"))
                            .withClientId(path.get("clientId"))
                            .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetClientActivity().handleRequest(request)
        );
    }
}
