import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { PerfilComponent } from './perfil.component';
import { PerfilRoutingModule } from './perfil-routing.module';
import {ButtonModule, CardModule, FormModule, GridModule, ModalModule} from "@coreui/angular";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    PerfilRoutingModule,
    ModalModule,
    FormModule,
    ButtonModule,
    CardModule,
    GridModule
  ],
  declarations: [ PerfilComponent ]
})
export class PerfilModule { }
