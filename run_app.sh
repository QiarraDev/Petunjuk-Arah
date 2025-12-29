#!/bin/bash
echo "Menjalankan Aplikasi Petunjuk Arah..."
echo "Menggunakan Portable Maven..."

# Set path ke portable maven
export M2_HOME=$(pwd)/tools/apache-maven-3.9.6
export PATH=$M2_HOME/bin:$PATH

# Verifikasi
mvn -version

# Jalankan Spring Boot
echo "Starting Spring Boot..."
mvn spring-boot:run
