package com.example.sellthatthing.emailsender;

public class MailBody {
    //<editor-fold desc="POST_REPLY_HTML"> // force intellij to fold/collapse the string
    public static final String POST_REPLY_HTML =
            """
                     <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
                        <html>
                          <head>
                            <!-- Compiled with Bootstrap Email version: 1.3.1 --><meta http-equiv="x-ua-compatible" content="ie=edge">
                            <meta name="x-apple-disable-message-reformatting">
                            <meta name="viewport" content="width=device-width, initial-scale=1">
                            <meta name="format-detection" content="telephone=no, date=no, address=no, email=no">
                            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                            <style type="text/css">
                              body,table,td{font-family:Helvetica,Arial,sans-serif !important}.ExternalClass{width:100%}.ExternalClass,.ExternalClass p,.ExternalClass span,.ExternalClass font,.ExternalClass td,.ExternalClass div{line-height:150%}a{text-decoration:none}*{color:inherit}a[x-apple-data-detectors],u+#body a,#MessageViewBody a{color:inherit;text-decoration:none;font-size:inherit;font-family:inherit;font-weight:inherit;line-height:inherit}img{-ms-interpolation-mode:bicubic}table:not([class^=s-]){font-family:Helvetica,Arial,sans-serif;mso-table-lspace:0pt;mso-table-rspace:0pt;border-spacing:0px;border-collapse:collapse}table:not([class^=s-]) td{border-spacing:0px;border-collapse:collapse}@media screen and (max-width: 600px){.w-full,.w-full>tbody>tr>td{width:100% !important}*[class*=s-lg-]>tbody>tr>td{font-size:0 !important;line-height:0 !important;height:0 !important}.s-2>tbody>tr>td{font-size:8px !important;line-height:8px !important;height:8px !important}.s-3>tbody>tr>td{font-size:12px !important;line-height:12px !important;height:12px !important}.s-5>tbody>tr>td{font-size:20px !important;line-height:20px !important;height:20px !important}.s-10>tbody>tr>td{font-size:40px !important;line-height:40px !important;height:40px !important}}
                            </style>
                          </head>
                          <body class="bg-dark" style="outline: 0; width: 100%; min-width: 100%; height: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; font-family: Helvetica, Arial, sans-serif; line-height: 24px; font-weight: normal; font-size: 16px; -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box; color: #000000; margin: 0; padding: 0; border-width: 0;" bgcolor="#1a202c">
                            <table class="bg-dark body" valign="top" role="presentation" border="0" cellpadding="0" cellspacing="0" style="outline: 0; width: 100%; min-width: 100%; height: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; font-family: Helvetica, Arial, sans-serif; line-height: 24px; font-weight: normal; font-size: 16px; -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box; color: #000000; margin: 0; padding: 0; border-width: 0;" bgcolor="#1a202c">
                              <tbody>
                                <tr>
                                  <td valign="top" style="line-height: 24px; font-size: 16px; margin: 0;" align="left" bgcolor="#1a202c">
                                    <table class="container" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                                      <tbody>
                                        <tr>
                                          <td align="center" style="line-height: 24px; font-size: 16px; margin: 0; padding: 0 16px;">
                                            <!--[if (gte mso 9)|(IE)]>
                                              <table align="center" role="presentation">
                                                <tbody>
                                                  <tr>
                                                    <td width="600">
                                            <![endif]-->
                                            <table align="center" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%; max-width: 600px; margin: 0 auto;">
                                              <tbody>
                                                <tr>
                                                  <td style="line-height: 24px; font-size: 16px; margin: 0;" align="left">
                                                    <table class="s-10 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                      <tbody>
                                                        <tr>
                                                          <td style="line-height: 40px; font-size: 40px; width: 100%; height: 40px; margin: 0;" align="left" width="100%" height="40">
                                                            &#160;
                                                          </td>
                                                        </tr>
                                                      </tbody>
                                                    </table>
                                                    <table class="card" role="presentation" border="0" cellpadding="0" cellspacing="0" style="border-radius: 6px; border-collapse: separate !important; width: 100%; overflow: hidden; border: 1px solid #e2e8f0;" bgcolor="#ffffff">
                                                      <tbody>
                                                        <tr>
                                                          <td style="line-height: 24px; font-size: 16px; width: 100%; margin: 0;" align="left" bgcolor="#ffffff">
                                                            <table class="card-body bg-dark text-white" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%; color: #ffffff;" bgcolor="#1a202c">
                                                              <tbody>
                                                                <tr>
                                                                  <td style="line-height: 24px; font-size: 16px; width: 100%; color: #ffffff; margin: 0; padding: 20px;" align="left" bgcolor="#1a202c">
                                                                    <h1 class="h3" style="padding-top: 0; padding-bottom: 0; font-weight: 500; vertical-align: baseline; font-size: 28px; line-height: 33.6px; margin: 0;" align="left">Reply to your Listing: $listingTitle</h1>
                                                                    <table class="s-2 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                      <tbody>
                                                                        <tr>
                                                                          <td style="line-height: 8px; font-size: 8px; width: 100%; height: 8px; margin: 0;" align="left" width="100%" height="8">
                                                                            &#160;
                                                                          </td>
                                                                        </tr>
                                                                      </tbody>
                                                                    </table>
                                                                    <h5 class="text-light" style="color: #f7fafc; padding-top: 0; padding-bottom: 0; font-weight: 500; vertical-align: baseline; font-size: 20px; line-height: 24px; margin: 0;" align="left">Hi $firstName, someone is interested in your listing</h5>
                                                                    <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                      <tbody>
                                                                        <tr>
                                                                          <td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">
                                                                            &#160;
                                                                          </td>
                                                                        </tr>
                                                                      </tbody>
                                                                    </table>
                                                                    <table class="hr" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                                                                      <tbody>
                                                                        <tr>
                                                                          <td style="line-height: 24px; font-size: 16px; border-top-width: 1px; border-top-color: #e2e8f0; border-top-style: solid; height: 1px; width: 100%; margin: 0;" align="left">
                                                                          </td>
                                                                        </tr>
                                                                      </tbody>
                                                                    </table>
                                                                    <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                      <tbody>
                                                                        <tr>
                                                                          <td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">
                                                                            &#160;
                                                                          </td>
                                                                        </tr>
                                                                      </tbody>
                                                                    </table>
                                                                    <div class="space-y-3">
                                                                      <p class="" style="line-height: 24px; font-size: 16px; width: 100%; margin: 0;" align="left">Message from $messageFrom:</p>
                                                                      <table class="s-3 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                        <tbody>
                                                                          <tr>
                                                                            <td style="line-height: 12px; font-size: 12px; width: 100%; height: 12px; margin: 0;" align="left" width="100%" height="12">
                                                                              &#160;
                                                                            </td>
                                                                          </tr>
                                                                        </tbody>
                                                                      </table>
                                                                      <p class="text-white-700" style="line-height: 24px; font-size: 16px; width: 100%; margin: 0;" align="left">
                                                                        $messageBody
                                                                      </p>
                                                                      <table class="s-3 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                        <tbody>
                                                                          <tr>
                                                                            <td style="line-height: 12px; font-size: 12px; width: 100%; height: 12px; margin: 0;" align="left" width="100%" height="12">
                                                                              &#160;
                                                                            </td>
                                                                          </tr>
                                                                        </tbody>
                                                                      </table>
                                                                      <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                        <tbody>
                                                                          <tr>
                                                                            <td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">
                                                                              &#160;
                                                                            </td>
                                                                          </tr>
                                                                        </tbody>
                                                                      </table>
                                                                      <table class="hr" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                                                                        <tbody>
                                                                          <tr>
                                                                            <td style="line-height: 24px; font-size: 16px; border-top-width: 1px; border-top-color: #e2e8f0; border-top-style: solid; height: 1px; width: 100%; margin: 0;" align="left">
                                                                            </td>
                                                                          </tr>
                                                                        </tbody>
                                                                      </table>
                                                                      <table class="s-5 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                                        <tbody>
                                                                          <tr>
                                                                            <td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;" align="left" width="100%" height="20">
                                                                              &#160;
                                                                            </td>
                                                                          </tr>
                                                                        </tbody>
                                                                      </table>
                                                                      <p class="text-light" style="line-height: 24px; font-size: 16px; color: #f7fafc; width: 100%; margin: 0;" align="left">Reply to this email to send your response.</p>
                                                                    </div>
                                                                  </td>
                                                                </tr>
                                                              </tbody>
                                                            </table>
                                                          </td>
                                                        </tr>
                                                      </tbody>
                                                    </table>
                                                    <table class="s-10 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;" width="100%">
                                                      <tbody>
                                                        <tr>
                                                          <td style="line-height: 40px; font-size: 40px; width: 100%; height: 40px; margin: 0;" align="left" width="100%" height="40">
                                                            &#160;
                                                          </td>
                                                        </tr>
                                                      </tbody>
                                                    </table>
                                                  </td>
                                                </tr>
                                              </tbody>
                                            </table>
                                            <!--[if (gte mso 9)|(IE)]>
                                            </td>
                                          </tr>
                                        </tbody>
                                      </table>
                                            <![endif]-->
                                          </td>
                                        </tr>
                                      </tbody>
                                    </table>
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </body>
                        </html>
                    """;
    //</editor-fold>

    //<editor-fold desc="VERIFY_CODE_HTML"> // force intellij to fold/collapse the string
    public static final String VERIFY_CODE_HTML =
            """
                     <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
                               <html>
                               <head>
                               	<!-- Compiled with Bootstrap Email version: 1.3.1 -->
                               	<meta http-equiv="x-ua-compatible" content="ie=edge">
                               	<meta name="x-apple-disable-message-reformatting">
                               	<meta name="viewport" content="width=device-width, initial-scale=1">
                               	<meta name="format-detection" content="telephone=no, date=no, address=no, email=no">
                               	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
                               	<style type="text/css">
                                       body, table, td {
                                           font-family: Helvetica, Arial, sans-serif !important
                                       }
                               
                                       .ExternalClass {
                                           width: 100%
                                       }
                               
                                       .ExternalClass, .ExternalClass p, .ExternalClass span, .ExternalClass font, .ExternalClass td, .ExternalClass div {
                                           line-height: 150%
                                       }
                               
                                       a {
                                           text-decoration: none
                                       }
                               
                                       * {
                                           color: inherit
                                       }
                               
                                       a[x-apple-data-detectors], u + #body a, #MessageViewBody a {
                                           color: inherit;
                                           text-decoration: none;
                                           font-size: inherit;
                                           font-family: inherit;
                                           font-weight: inherit;
                                           line-height: inherit
                                       }
                               
                                       img {
                                           -ms-interpolation-mode: bicubic
                                       }
                               
                                       table:not([class^=s-]) {
                                           font-family: Helvetica, Arial, sans-serif;
                                           mso-table-lspace: 0pt;
                                           mso-table-rspace: 0pt;
                                           border-spacing: 0px;
                                           border-collapse: collapse
                                       }
                               
                                       table:not([class^=s-]) td {
                                           border-spacing: 0px;
                                           border-collapse: collapse
                                       }
                               
                                       @media screen and (max-width: 600px) {
                                           .row-responsive.row {
                                               margin-right: 0 !important
                                           }
                               
                                           td.col-lg-4 {
                                               display: block;
                                               width: 100% !important;
                                               padding-left: 0 !important;
                                               padding-right: 0 !important
                                           }
                               
                                           .w-full, .w-full > tbody > tr > td {
                                               width: 100% !important
                                           }
                               
                                           .w-40, .w-40 > tbody > tr > td {
                                               width: 160px !important
                                           }
                               
                                           *[class*=s-lg-] > tbody > tr > td {
                                               font-size: 0 !important;
                                               line-height: 0 !important;
                                               height: 0 !important
                                           }
                               
                                           .s-2 > tbody > tr > td {
                                               font-size: 8px !important;
                                               line-height: 8px !important;
                                               height: 8px !important
                                           }
                               
                                           .s-3 > tbody > tr > td {
                                               font-size: 12px !important;
                                               line-height: 12px !important;
                                               height: 12px !important
                                           }
                               
                                           .s-5 > tbody > tr > td {
                                               font-size: 20px !important;
                                               line-height: 20px !important;
                                               height: 20px !important
                                           }
                               
                                           .s-10 > tbody > tr > td {
                                               font-size: 40px !important;
                                               line-height: 40px !important;
                                               height: 40px !important
                                           }
                                       }
                               	</style>
                               </head>
                               <body class="bg-dark"
                               	  style="outline: 0; width: 100%; min-width: 100%; height: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; font-family: Helvetica, Arial, sans-serif; line-height: 24px; font-weight: normal; font-size: 16px; -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box; color: #000000; margin: 0; padding: 0; border-width: 0;"
                               	  bgcolor="#1a202c">
                               	<table class="bg-dark body" valign="top" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               		   style="outline: 0; width: 100%; min-width: 100%; height: 100%; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; font-family: Helvetica, Arial, sans-serif; line-height: 24px; font-weight: normal; font-size: 16px; -moz-box-sizing: border-box; -webkit-box-sizing: border-box; box-sizing: border-box; color: #000000; margin: 0; padding: 0; border-width: 0;"
                               		   bgcolor="#1a202c">
                               		<tbody>
                               		<tr>
                               			<td valign="top" style="line-height: 24px; font-size: 16px; margin: 0;" align="left" bgcolor="#1a202c">
                               				<table class="container" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                               					<tbody>
                               					<tr>
                               						<td align="center" style="line-height: 24px; font-size: 16px; margin: 0; padding: 0 16px;">
                               							<!--[if (gte mso 9)|(IE)]>
                               							<table align="center" role="presentation">
                               								<tbody>
                               								<tr>
                               									<td width="600">
                               							<![endif]-->
                               							<table align="center" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               								   style="width: 100%; max-width: 600px; margin: 0 auto;">
                               								<tbody>
                               								<tr>
                               									<td style="line-height: 24px; font-size: 16px; margin: 0;" align="left">
                               										<table class="s-10 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;"
                               											   width="100%">
                               											<tbody>
                               											<tr>
                               												<td style="line-height: 40px; font-size: 40px; width: 100%; height: 40px; margin: 0;" align="left"
                               													width="100%" height="40">
                               													&#160;
                               												</td>
                               											</tr>
                               											</tbody>
                               										</table>
                               										<table class="card" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               											   style="border-radius: 6px; border-collapse: separate !important; width: 100%; overflow: hidden; border: 1px solid #e2e8f0;"
                               											   bgcolor="#ffffff">
                               											<tbody>
                               											<tr>
                               												<td style="line-height: 24px; font-size: 16px; width: 100%; margin: 0;" align="left"
                               													bgcolor="#ffffff">
                               													<table class="card-body" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               														   style="width: 100%;">
                               														<tbody>
                               														<tr>
                               															<td style="line-height: 24px; font-size: 16px; width: 100%; margin: 0; padding: 20px;"
                               																align="left">
                               																<h1 class="h3  text-center"
                               																	style="padding-top: 0; padding-bottom: 0; font-weight: 500; vertical-align: baseline; font-size: 28px; line-height: 33.6px; margin: 0;"
                               																	align="center">Hi $firstName, Your verification code is here</h1>
                               																<table class="s-2 w-full" role="presentation" border="0" cellpadding="0"
                               																	   cellspacing="0" style="width: 100%;" width="100%">
                               																	<tbody>
                               																	<tr>
                               																		<td style="line-height: 8px; font-size: 8px; width: 100%; height: 8px; margin: 0;"
                               																			align="left" width="100%" height="8">
                               																			&#160;
                               																		</td>
                               																	</tr>
                               																	</tbody>
                               																</table>
                               																<table class="s-5 w-full" role="presentation" border="0" cellpadding="0"
                               																	   cellspacing="0" style="width: 100%;" width="100%">
                               																	<tbody>
                               																	<tr>
                               																		<td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;"
                               																			align="left" width="100%" height="20">
                               																			&#160;
                               																		</td>
                               																	</tr>
                               																	</tbody>
                               																</table>
                               																<table class="hr" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               																	   style="width: 100%;">
                               																	<tbody>
                               																	<tr>
                               																		<td style="line-height: 24px; font-size: 16px; border-top-width: 1px; border-top-color: #e2e8f0; border-top-style: solid; height: 1px; width: 100%; margin: 0;"
                               																			align="left">
                               																		</td>
                               																	</tr>
                               																	</tbody>
                               																</table>
                               																<table class="s-5 w-full" role="presentation" border="0" cellpadding="0"
                               																	   cellspacing="0" style="width: 100%;" width="100%">
                               																	<tbody>
                               																	<tr>
                               																		<td style="line-height: 20px; font-size: 20px; width: 100%; height: 20px; margin: 0;"
                               																			align="left" width="100%" height="20">
                               																			&#160;
                               																		</td>
                               																	</tr>
                               																	</tbody>
                               																</table>
                               																<div class="space-y-3">
                               																	<p class="text-gray-700 text-center"
                               																	   style="line-height: 24px; font-size: 16px; color: #4a5568; width: 100%; margin: 0;"
                               																	   align="center">
                               																		Thank you for registering, your verification code is:
                               																	</p>
                               																	<table class="s-3 w-full" role="presentation" border="0" cellpadding="0"
                               																		   cellspacing="0" style="width: 100%;" width="100%">
                               																		<tbody>
                               																		<tr>
                               																			<td style="line-height: 12px; font-size: 12px; width: 100%; height: 12px; margin: 0;"
                               																				align="left" width="100%" height="12">
                               																				&#160;
                               																			</td>
                               																		</tr>
                               																		</tbody>
                               																	</table>
                               																	<div class="row row-responsive" style="margin-right: -24px;">
                               																		<table class="" role="presentation" border="0" cellpadding="0" cellspacing="0"
                               																			   style="table-layout: fixed; width: 100%;" width="100%">
                               																			<tbody>
                               																			<tr>
                               																				<td class="col-lg-4 text-center"
                               																					style="line-height: 24px; font-size: 16px; min-height: 1px; font-weight: normal; padding-right: 24px; width: 33.333333%; margin: 0;"
                               																					align="center" valign="top">
                               																					<table class="alert alert-primary text-center w-40"
                               																						   role="presentation" border="0" cellpadding="0"
                               																						   cellspacing="0"
                               																						   style="border-collapse: separate !important; width: 160px; text-align: center !important; border-width: 0;"
                               																						   width="160">
                               																						<tbody>
                               																						<tr>
                               																							<td style="line-height: 24px; font-size: 16px; border-radius: 4px; color: #012e70; width: 160px; margin: 0; padding: 12px 20px; border: 1px solid transparent;"
                               																								align="center" bgcolor="#d7e7ff" width="160">
                               																								<div>
                               																									<strong>$verificationCode</strong></div>
                               																							</td>
                               																						</tr>
                               																						</tbody>
                               																					</table>
                               																				</td>
                               																			</tr>
                               																			</tbody>
                               																		</table>
                               																	</div>
                               																	<table class="s-3 w-full" role="presentation" border="0" cellpadding="0"
                               																		   cellspacing="0" style="width: 100%;" width="100%">
                               																		<tbody>
                               																		<tr>
                               																			<td style="line-height: 12px; font-size: 12px; width: 100%; height: 12px; margin: 0;"
                               																				align="left" width="100%" height="12">
                               																				&#160;
                               																			</td>
                               																		</tr>
                               																		</tbody>
                               																	</table>
                               																	<p class="text-gray-700 text-center"
                               																	   style="line-height: 24px; font-size: 16px; color: #4a5568; width: 100%; margin: 0;"
                               																	   align="center">
                               																		This code expires in 24 hours
                               																	</p>
                               																</div>
                               															</td>
                               														</tr>
                               														</tbody>
                               													</table>
                               												</td>
                               											</tr>
                               											</tbody>
                               										</table>
                               										<table class="s-10 w-full" role="presentation" border="0" cellpadding="0" cellspacing="0" style="width: 100%;"
                               											   width="100%">
                               											<tbody>
                               											<tr>
                               												<td style="line-height: 40px; font-size: 40px; width: 100%; height: 40px; margin: 0;" align="left"
                               													width="100%" height="40">
                               													&#160;
                               												</td>
                               											</tr>
                               											</tbody>
                               										</table>
                               									</td>
                               								</tr>
                               								</tbody>
                               							</table>
                               							<!--[if (gte mso 9)|(IE)]>
                               							</td>
                               							</tr>
                               							</tbody>
                               							</table>
                               							<![endif]-->
                               						</td>
                               					</tr>
                               					</tbody>
                               				</table>
                               			</td>
                               		</tr>
                               		</tbody>
                               	</table>
                               </body>
                               </html>
                               
                    """;
    //</editor-fold>

}
