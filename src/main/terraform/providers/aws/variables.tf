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

# ==== Region and AV ====
variable "default_region" {
	description = "Default region to setup"
	default = "eu-central-1"
}
variable "regions" {
	description = "Regions to setup"
	default = ["eu-west-1","eu-west-2","eu-west-3"]
}	
variable "default_ip_range" {
	description = "PZ IP range from my IP range"
	default = "138.199.69.187/32"
}

# ==== Applications ====
variable "applications" {
	description = "The load balancer configuration for the application"
	type = "map"
	default = {
		jdemo.protocol	 			= "HTTP"
		jdemo.ingressPort			= "80"
		jdemo.egressPort			= ""
		jdemo.allowIPv4Ingress		= "0.0.0.0/0"
		jdemo.allowIPv6Ingress		= "::/0"
		jdemo.allowIPv4Egress		= "0.0.0.0/0"
		jdemo.allowIPv6Egress		= "::/0"
		
		jdemo.deregistration_delay  = "300"
		jdemo.health_check_path	 	= "/jdemo"
		jdemo.action_type			= "forward"
	}
}

	
