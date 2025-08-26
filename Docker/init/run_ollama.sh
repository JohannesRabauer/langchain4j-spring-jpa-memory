#!/bin/bash

echo "Starting Ollama server..."
ollama serve &  # Start Ollama in the background

echo "Waiting for Ollama server to be active..."
while [ -z "$(ollama list | grep 'NAME')" ]; do
  sleep 1
done

echo "Ollama is ready, starting the model..."
ollama run llama3.2:1b

# Wait indefinitely so the container stays alive
wait