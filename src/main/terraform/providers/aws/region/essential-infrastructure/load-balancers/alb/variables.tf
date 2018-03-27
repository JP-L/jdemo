#/*
# * Copyright (c) 2018 JP-L, https://www.jp-l.org/
# *
# * Permission is hereby granted, free of charge, to any person obtaining
# * a copy of this software and associated documentation files (the
# * "Software"), to deal in the Software without restriction, including
# * without limitation the rights to use, copy, modify, merge, publish,
# * distribute, sublicense, and/or sell copies of the Software, and to
# * permit persons to whom the Software is furnished to do so, subject to
# * the following conditions:
# *
# * The above copyright notice and this permission notice shall be
# * included in all copies or substantial portions of the Software.
#
# * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
# * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
# * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
# * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
# * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
# * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
# * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
#*/

#
# ALB variables
#
variable "vpc_id" {
  description = "VPC ID generated on creation of the VPC"
}
variable "alb_id" {
  description = ""
}
variable "alb_name" {
  description = ""
}
variable "sg_alb_id" {
  description = ""
}

# Application specific
variable "ingressPort" {
	description = ""
}
variable "protocol" {
	description = ""
}
variable "allowIPv4Ingress" {
	description = ""
}
variable "allowIPv6Ingress" {
	description = ""
}
variable "allowIPv4Egress" {
	description = ""
}
variable "allowIPv6Egress" {
	description = ""
}
variable "deregistration_delay" {
	description = ""
}
variable "health_check_path" {
	description = ""
}
variable "action_type" {
	description = ""
}
