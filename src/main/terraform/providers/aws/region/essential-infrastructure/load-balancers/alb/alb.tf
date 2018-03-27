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
# Application specific Load Balancer configuration
#
resource "aws_lb_target_group" "jdemo_tg" {
	name                 	= "${var.alb_name}-TG"
	port                 	= "${var.ingressPort}"
	protocol             	= "${var.protocol}"
	vpc_id               	= "${var.vpc_id}"
	deregistration_delay 	= "${var.deregistration_delay}"
	
	health_check {
		path     			= "${var.health_check_path}"
		protocol 			= "${var.protocol}"
	}

	tags {
		Name 				= "${var.alb_name}"
	}
}

resource "aws_lb_listener" "jdemo_listener" {
	load_balancer_arn 		= "${var.alb_id}"
	port              		= "${var.ingressPort}"
	protocol          		= "${var.protocol}"
	#ssl_policy
	
	default_action {
		target_group_arn 	= "${aws_lb_target_group.jdemo_tg.id}"
		type             	= "${var.action_type}"
	}
}

resource "aws_security_group_rule" "jdemo_http_from_anywhere" {
	type              		= "ingress"
	from_port         		= "${var.ingressPort}"
	to_port           		= "${var.ingressPort}"
	protocol          		= "tcp"
	cidr_blocks       		= ["${var.allowIPv4Ingress}"]
	ipv6_cidr_blocks		= ["${var.allowIPv6Ingress}"]
	security_group_id 		= "${var.sg_alb_id}" 
}

resource "aws_security_group_rule" "outbound_internet_access" {
	type              		= "egress"
	from_port         		= 0
	to_port           		= 0
	protocol          		= "-1"
	cidr_blocks       		= ["${var.allowIPv4Egress}"]
	ipv6_cidr_blocks  		= ["${var.allowIPv6Egress}"]
	security_group_id 		= "${var.sg_alb_id}"
}

