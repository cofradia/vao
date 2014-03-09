class V1::EventController < ApplicationController
	skip_before_filter :verify_authenticity_token,
                     :if => Proc.new { |c| c.request.format == 'application/json' }

  respond_to :json

  def create
  	p "asdasda"
		event_data = params["event"]
		user = User.find_by_authentication_token(params["auth_token"])
		if !user.nil?
			place_obj = Place.new(:latitude => event_data["event_place_latitude"], :longitude => event_data["event_place_longitude"], :name => event_data["event_place_name"])
			if place_obj.save!
				puts place_obj.inspect
				event_obj = Event.new(:name => event_data["event_name"], 
															:description => event_data["event_description"], 
															:id_category => event_data["event_category"], 
															:place_id => place_obj.id, 
															:user_id => user.id)
				if event_obj.save!
					date_by_event = EventDate.new(:event_id => event_obj.id, 
																				:start_date => event_data["event_start_date"], 
																				:end_date => event_data["event_end_date"])
					if date_by_event.save!
						render :json => {:success => true,:event_obj => event_obj}
			    else
	  		    render :status => :unprocessable_entity,:json => { :success => false, :info => @message.errors,
						:data => {} }
			    end			
				else
					render :status => :unprocessable_entity,:json => { :success => false, :info => @message.errors,
						:data => {} }
				end
			else
				render :status => :unprocessable_entity,:json => { :success => false, :info => @message.errors,
						:data => {} }
			end
		else
			render :status => :unprocessable_entity,:json => { :success => false, :info => "User not logged in",
						:data => {} }
		end
  end

  def index
    update_time = params[:update_time]
    events = Event.where("created_at >= ? or update_at >= ? ", update_time)
  end

  def show
    event_id = params[:event_id]
    event = Event.find(event_id)
  end  

end
