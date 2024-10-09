
# Throttling Extension for the Token Endpoint

This project implements a custom token throttling extension for the WSO2 API Manager, enabling the injection of custom properties into the throttling request data. By creating a custom filter using the `ExtensionListener` interface, we can process requests before they are handled by the throttling handler.

## Features

- **Client ID Extraction**: Extracts the client ID from the Basic Authorization header in requests to the `/token/1.0.0` endpoint.
- **Custom Properties Injection**: Allows the injection of custom properties into the throttling request data.
- **Pre-Processing Capability**: Intercepts API requests for custom processing before they reach the throttling handler.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

### Installation

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd token-throttling-extension
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Deploy the extension**:
 - Copy the generated JAR file to the `<APIM_HOME>/repository/components/dropins` directory in the WSO2 API Manager.

### Configuration

To integrate the custom filter with the Gateway, add the following configuration to the `deployment.toml` file, directing it to your custom implementation:

```toml
[[apim.extension.listener]]
type = "THROTTLING"
class = "org.wso2.carbon.test.TokenThrottlingExtensionImpl"
```

Example throttling request data with the client ID included:

```json
{
    "appKey": "127.0.0.1:anonymous@carbon.super",
    "apiKey": "/token/1.0.0:1.0.0",
    "subscriptionKey": "127.0.0.1:/token/1.0.0:1.0.0:Unauthenticated",
    "resourceKey": "/token/1.0.0/1.0.0/*:POST",
    "userId": "anonymous@carbon.super",
    "apiContext": "/token/1.0.0",
    "apiVersion": "1.0.0",
    "properties": {
        "clientID": "uARHqx1HfY6XGqKjfIY6_HTNIHIa",
        "ipv6": 0,
        "ip": 2130706433
    }
}
```
