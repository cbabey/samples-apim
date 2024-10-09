package org.wso2.carbon.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHeaders;
import org.wso2.carbon.apimgt.common.gateway.dto.ExtensionResponseDTO;
import org.wso2.carbon.apimgt.common.gateway.dto.ExtensionResponseStatus;
import org.wso2.carbon.apimgt.common.gateway.dto.RequestContextDTO;
import org.wso2.carbon.apimgt.common.gateway.dto.ResponseContextDTO;
import org.wso2.carbon.apimgt.common.gateway.extensionlistener.ExtensionListener;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.UIManager.put;

public class TokenThrottlingExtensionImpl implements ExtensionListener {
    private static final Log log = LogFactory.getLog(TokenThrottlingExtensionImpl.class);

    private String extractTheClientIDFromAuthHeader( Map<String, String> headers){
        String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader!=null && !authorizationHeader.isEmpty()){
            if (authorizationHeader.startsWith("Basic ")) {
                // Extract the base64 encoded part
                String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();

                // Decode the base64 string
                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
                String credentials = new String(decodedBytes);

                // Split the credentials into clientID and clientSecret
                String[] parts = credentials.split(":", 2);
                if (parts.length == 2) {
                    String clientID = parts[0];
                    return clientID;
                } else {
                    log.debug("Invalid credentials format.");
                }
            } else {
                log.debug("Authorization header is not Basic.");
            }
        }
        return null;
    }
    @Override
    public ExtensionResponseDTO preProcessRequest(RequestContextDTO requestContextDTO) {
        ExtensionResponseDTO responseDTO = null; // Initialize responseDTO to null

        // Check if the API request context matches the specified endpoint
        if (requestContextDTO.getApiRequestInfo().getContext().equals("/token/1.0.0")) {
            // Retrieve the headers from the request message information
            Map<String, String> headers = requestContextDTO.getMsgInfo().getHeaders();

            // Extract the client ID from the Authorization header
            String clientID = extractTheClientIDFromAuthHeader(headers);

            // If a client ID was successfully extracted
            if (clientID != null) {
                responseDTO = new ExtensionResponseDTO(); // Create a new ExtensionResponseDTO
                // Set a custom property to include the extracted client ID
                responseDTO.setCustomProperty(new HashMap<String, Object>() {{ put("clientID", clientID); }});
                // Set the response status to continue processing
                responseDTO.setResponseStatus(ExtensionResponseStatus.CONTINUE.toString());
            }
        }

        // Return the response DTO (maybe null if no client ID was found)
        return responseDTO;
    }

    @Override
    public ExtensionResponseDTO postProcessRequest(RequestContextDTO requestContextDTO) {

        return null;
    }

    @Override
    public ExtensionResponseDTO preProcessResponse(ResponseContextDTO responseContextDTO) {

        return null;
    }

    @Override
    public ExtensionResponseDTO postProcessResponse(ResponseContextDTO responseContextDTO) {

        return null;
    }

    @Override
    public String getType() {

        return null;
    }
}
