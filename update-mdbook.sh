#!/usr/bin/env bash

# Check if cargo is installed
if ! command -v cargo &> /dev/null
then
    echo "cargo could not be found, please install Rust and Cargo."
    exit 1
fi

# check if cargo-binstall is installed; if not, install it
if ! command -v cargo-binstall &> /dev/null
then
    echo "cargo-binstall could not be found, installing it..."
    curl -L --proto '=https' --tlsv1.2 -sSf https://raw.githubusercontent.com/cargo-bins/cargo-binstall/main/install-from-binstall-release.sh | bash
fi

cargo binstall -y --disable-telemetry mdbook \
              mdbook-mermaid \
              mdbook-alerts \
              mdbook-katex

cargo install --locked --path ./preprocessors/rust/mdbook-codeblock-tabs