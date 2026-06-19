variable "project_id" {
  description = "El ID del proyecto de GCP"
  type        = string
}

variable "region" {
  description = "La región de GCP donde se desplegarán los recursos"
  type        = string
  default     = "us-central1"
}

variable "cluster_name" {
  description = "El nombre del clúster de GKE"
  type        = string
  default     = "techretail-cluster"
}
