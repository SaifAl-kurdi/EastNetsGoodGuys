PRIVATE_KEY_NAME="private.key"
PEM_KEY_NAME="testKey.pem"
PUBLIC_KEY_NAME="public.key"
KEY_DIR="META-INF/resources"

# Enable debugging
set -e
trap 'echo "An error occurred. Exiting..."' ERR

# Create the directory if it doesn't exist
if [ ! -d "$KEY_DIR" ]; then
    echo "Creating directory: $KEY_DIR"
    mkdir -p "$KEY_DIR"
else
    echo "Directory already exists: $KEY_DIR"
fi

# Generate a private key if it doesn't exist
if [ ! -f "$PRIVATE_KEY_NAME" ]; then
    echo "Generating private key..."
    openssl genrsa -out "$PRIVATE_KEY_NAME" 2048
else
    echo "Private key already exists: $PRIVATE_KEY_NAME"
fi

# Convert private key to PKCS#8 format if it doesn't exist
if [ ! -f "$KEY_DIR/$PEM_KEY_NAME" ]; then
    echo "Converting private key to PKCS#8 format..."
    openssl pkcs8 -topk8 -nocrypt -in "$PRIVATE_KEY_NAME" -out "$KEY_DIR/$PEM_KEY_NAME"
    if [ $? -eq 0 ]; then
        echo "testKey.pem generated successfully."
    else
        echo "Error: Failed to generate testKey.pem."
        exit 1
    fi
else
    echo "PEM file already exists: $KEY_DIR/$PEM_KEY_NAME"
fi

# Generate a public key if it doesn't exist
if [ ! -f "$PUBLIC_KEY_NAME" ]; then
    echo "Generating public key..."
    openssl rsa -in "$PRIVATE_KEY_NAME" -pubout -out "$PUBLIC_KEY_NAME"
else
    echo "Public key already exists: $PUBLIC_KEY_NAME"
fi

# Final confirmation
echo "Keys are set up successfully in $KEY_DIR"