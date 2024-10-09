

# WSO2 API Manager Samples

This repository contains various samples related to WSO2 API Manager. The samples demonstrate different functionalities and use cases that can be implemented with WSO2 API Manager to enhance API management and security.

## Repository Structure

- **ThrottlingExtensionForTokenEndpoint**: This sample implements a custom token throttling extension for WSO2 API Manager. It enables the injection of custom properties into the throttling request data, allowing for enhanced control and monitoring of API requests. The extension processes incoming requests to the `/token/1.0.0` endpoint and extracts the client ID from the authorization header, which can be used in custom throttling policies. For more details, refer to the [ThrottlingExtensionForTokenEndpoint](ThrottlingExtensionForTokenEndpoint) directory.

## Contributing

Contributions are welcome! If you have suggestions for improvements or additional samples, please feel free to submit a pull request or open an issue.
