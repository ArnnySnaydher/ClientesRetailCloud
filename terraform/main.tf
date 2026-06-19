provider "google" {
  project = var.project_id
  region  = var.region
}

# Habilitar la API de Artifact Registry
resource "google_project_service" "artifact_registry_api" {
  service = "artifactregistry.googleapis.com"
  disable_on_destroy = false
}

# Habilitar la API de Kubernetes Engine
resource "google_project_service" "kubernetes_engine_api" {
  service = "container.googleapis.com"
  disable_on_destroy = false
}

# Repositorio en Artifact Registry para almacenar la imagen Docker
resource "google_artifact_registry_repository" "techretail_repo" {
  location      = var.region
  repository_id = "techretail-repo"
  description   = "Repositorio Docker para microservicios de TechRetail"
  format        = "DOCKER"
  depends_on    = [google_project_service.artifact_registry_api]
}

# Clúster de GKE (Autopilot para administración automática de nodos)
resource "google_container_cluster" "primary" {
  name     = var.cluster_name
  location = var.region

  # Habilitamos GKE Autopilot (Google administra los nodos, pagas por uso de los pods)
  enable_autopilot = true

  # Facilitar el borrado del clúster
  deletion_protection = false

  depends_on = [google_project_service.kubernetes_engine_api]
}
