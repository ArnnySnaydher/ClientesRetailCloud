output "artifact_registry_url" {
  description = "URL base para hacer push de imágenes a Artifact Registry"
  value       = "${var.region}-docker.pkg.dev/${var.project_id}/${google_artifact_registry_repository.techretail_repo.repository_id}"
}

output "kubernetes_cluster_name" {
  description = "El nombre del clúster GKE"
  value       = google_container_cluster.primary.name
}

output "kubernetes_cluster_endpoint" {
  description = "El endpoint del clúster GKE"
  value       = google_container_cluster.primary.endpoint
}
