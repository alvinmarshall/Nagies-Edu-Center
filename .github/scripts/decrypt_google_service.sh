#!/bin/sh
    
    # Decrypt the file
    mkdir $GITHUB_WORKSPACE/secrets
    # --batch to prevent interactive command --yes to assume "yes" for questions
    gpg --quiet --batch --yes --decrypt --passphrase="$GOOGLE_SERVICE_KEY" --output app/google-services.json app/google-services.json.gpg
