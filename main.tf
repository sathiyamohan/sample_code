#Create VPC for the instance.
resource "aws_vpc" "my_vpc" {
  cidr_block = "10.0.0.0/16"
  tags = {
    Name = "TF_VPC_1"
  }
}