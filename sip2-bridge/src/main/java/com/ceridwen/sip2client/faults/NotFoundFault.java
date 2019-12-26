/*
 * Copyright 2019 Ceridwen Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ceridwen.sip2client.faults;

/**
 *
 * @author Ceridwen Limited
 */
public class NotFoundFault extends Fault {

  /**
   * Constructor
   * 
   * @param message message describing error
   * @param debug   return additional debugging information
   */
  public NotFoundFault(String message, Boolean debug) {
    super(message, debug);
  }

}