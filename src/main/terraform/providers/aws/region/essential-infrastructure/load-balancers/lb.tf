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
# LOAD BALANCERS
#
module "alb_paz" {
	source = "./alb"

	# Available from environment
	vpc_id					= "${var.vpc_id}"
	alb_id					= "${var.alb_config[format("%s_%s_%s",var.region,"alb","id")]}"
	alb_name				= "${var.alb_config[format("%s_%s_%s",var.region,"alb","name")]}"
	sg_alb_id				= "${var.alb_config[format("%s_%s_%s",var.region,"alb","sg_id")]}"
	
	# Application specific stuff
	ingressPort				= "${var.applications["jdemo.ingressPort"]}"
	protocol				= "${var.applications["jdemo.protocol"]}"
	
	allowIPv4Ingress		= "${var.applications["jdemo.allowIPv4Ingress"]}"
	allowIPv6Ingress		= "${var.applications["jdemo.allowIPv6Ingress"]}"
	allowIPv4Egress			= "${var.applications["jdemo.allowIPv4Egress"]}"
	allowIPv6Egress			= "${var.applications["jdemo.allowIPv6Egress"]}"
	
	deregistration_delay	= "${var.applications["jdemo.deregistration_delay"]}"
	health_check_path		= "${var.applications["jdemo.health_check_path"]}"
	action_type				= "${var.applications["jdemo.action_type"]}"
	
}

