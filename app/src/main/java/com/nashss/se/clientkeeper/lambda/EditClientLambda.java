package com.nashss.se.clientkeeper.lambda;
import com.nashss.se.clientkeeper.activity.requests.EditClientRequest;
import com.nashss.se.clientkeeper.activity.results.EditClientResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EditClientLambda extends LambdaActivityRunner<EditClientRequest, EditClientResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditClientRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditClientRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromBody(EditClientRequest.class),
            (request, serviceComponent) ->
                serviceComponent.provideEditClientActivity().handleRequest(request)
        );
    }
}
